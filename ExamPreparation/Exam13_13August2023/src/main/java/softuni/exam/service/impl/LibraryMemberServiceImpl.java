package softuni.exam.service.impl;


import org.springframework.stereotype.Service;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private static final String FILE_PATH = "src/main/resources/files/json/library-members.json";
    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        return null;
    }
}
