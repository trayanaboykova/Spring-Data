package softuni.exam.service.impl;


import softuni.exam.service.LibraryMemberService;

import java.io.IOException;

public class LibraryMemberServiceImpl implements LibraryMemberService {

    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return null;
    }

    @Override
    public String importLibraryMembers() throws IOException {
        return null;
    }
}
