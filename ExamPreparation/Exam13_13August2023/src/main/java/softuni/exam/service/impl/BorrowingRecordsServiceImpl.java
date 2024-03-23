package softuni.exam.service.impl;


import org.springframework.stereotype.Service;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.service.BorrowingRecordsService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {
    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
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
        return null;
    }

    @Override
    public String exportBorrowingRecords() {
        return null;
    }
}
