package softuni.exam.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.BorrowingRecordRootDto;
import softuni.exam.models.dto.xmls.BorrowingRecordSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

            List<BorrowingRecordSeedDto> sortedRecords = borrowingRootDto
                    .getBorrowingRecords()
                    .stream()
                    .sorted(Comparator.comparing(borrowingRecord -> borrowingRecord.getMember().getId()))
                    .collect(Collectors.toList());

            for (BorrowingRecordSeedDto borrowingDto : sortedRecords) {
                if (borrowingDto.getBorrowDate() == null) {
                    sb.append("Invalid borrowing record\n");
                    continue;
                }
                if (borrowingDto.getReturnDate() == null) {
                    borrowingDto.setReturnDate(LocalDate.now());
                    continue;
                }

                boolean isValid = this.validationUtil.isValid(borrowingDto);

                Book book = this.bookRepository
                        .findAllByTitle(borrowingDto.getBook().getTitle())
                        .orElse(null);

                LibraryMember member = this.libraryMemberRepository
                        .findMemberById(borrowingDto.getMember().getId())
                        .orElse(null);

                if (book == null || member == null) {
                    isValid = false;
                }

                if (isValid) {
                    sb.append(String.format("Successfully imported borrowing record %s - %s\n",
                            borrowingDto.getBook().getTitle(),
                            borrowingDto.getBorrowDate()));

                    LocalDate borrowDate = borrowingDto.getBorrowDate();
                    LocalDate returnDate = borrowingDto.getReturnDate();

                    BorrowingRecord record = this.modelMapper.map(borrowingDto, BorrowingRecord.class);
                    record.setBooks(book);
                    record.setLibraryMember(member);
                    record.setBorrowDate(borrowDate);
                    record.setReturnDate(returnDate);

                    this.borrowingRecordRepository.saveAndFlush(record);
                }
                else {
                    sb.append("Invalid borrowing record\n");
                }
            }

        return sb.toString();
    }

    @Override
    public String exportBorrowingRecords() {
        return null;
    }
}
