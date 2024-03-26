package softuni.exam.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.BorrowingRecordRootDto;
import softuni.exam.models.dto.xmls.BorrowingRecordSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(BorrowingRecordRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BorrowingRecordRootDto borrowingRootDto = (BorrowingRecordRootDto) unmarshaller.unmarshal(new File(FILE_PATH));

        for (BorrowingRecordSeedDto borrowingRecordSeedDto : borrowingRootDto.getBorrowingRecords()) {

            String title = borrowingRecordSeedDto.getBook().getTitle();
            Optional<Book> optionalBook = this.bookRepository.findAllByTitle(title);

            long memberID = borrowingRecordSeedDto.getLibraryMembers().getId();
            Optional<LibraryMember> optionalLibraryMember = this.libraryMemberRepository.findMemberById(memberID);

            if (!this.validationUtil.isValid(borrowingRecordSeedDto) || optionalBook.isEmpty() || optionalLibraryMember.isEmpty()) {
                sb.append("Invalid borrowing record\n");
                continue;
            }

            BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordSeedDto, BorrowingRecord.class);
            borrowingRecord.setBooks(optionalBook.get());
            borrowingRecord.setLibraryMember(optionalLibraryMember.get());

            this.borrowingRecordRepository.saveAndFlush(borrowingRecord);
            sb.append(String.format("Successfully imported borrowing record %s - %s\n",
                    borrowingRecord.getBooks().getTitle(),
                    borrowingRecord.getBorrowDate()));

        }
        return sb.toString();

    }

    @Override
    public String exportBorrowingRecords() {

        StringBuilder sb = new StringBuilder();
        List<BorrowingRecord> records = borrowingRecordRepository
                .findAllByBooksGenreAndBorrowDateBefore(Genre.SCIENCE_FICTION, LocalDate.of(2021, 9, 10));

        for (BorrowingRecord record : records) {
           Book book = record.getBooks();
           LibraryMember libraryMember = record.getLibraryMember();
            sb.append(String.format("Book title: %s\n", book.getTitle()));
            sb.append(String.format("*Book author: %s\n", book.getAuthor()));
            sb.append(String.format("**Date borrowed: %s\n", record.getBorrowDate()));
            sb.append(String.format("***Borrowed by: %s %s\n",
                    libraryMember.getFirstName(),
                    libraryMember.getLastName()));
        }

        return sb.toString().trim();
    }
}
