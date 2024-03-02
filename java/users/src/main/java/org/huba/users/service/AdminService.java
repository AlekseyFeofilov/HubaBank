package org.huba.users.service;

import org.huba.users.exception.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class AdminService {
    public String getAllUserPage() {
        throw new NotImplementedException();
    }
}
