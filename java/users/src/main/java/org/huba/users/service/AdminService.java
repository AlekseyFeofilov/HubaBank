package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.exception.BadRequestException;
import org.huba.users.exception.NotFoundException;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.model.Privilege;
import org.huba.users.model.User;
import org.huba.users.repository.PrivilegesRepository;
import org.huba.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PrivilegesRepository privilegesRepository;
    private String template = "" +
            "<!doctype html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "</head>\n" +
            "<body>\n" +
            "tablePlace"+
            "</body>\n" +
            "</html>";

    public String getAllUserPage() {
        String table = "";
        table += "<table style='border: 1px solid #777777;'>";
        table += "<tr>";
        table += "<td>";
        table += "getId";
        table += "</td>";

        table += "<td>";
        table += "getPhone";
        table += "</td>";

        table += "<td>";
        table += "getFirstName";
        table += "</td>";

        table += "<td>";
        table += "getSecondName";
        table += "</td>";

        table += "<td>";
        table += "getThirdName";
        table += "</td>";

        table += "<td>";
        table += "getPasswordHash";
        table += "</td>";

        table += "<td>";
        table += "getPrivileges";
        table += "</td>";

        table += "</tr>";
        for(User user : userRepository.findAll()) {
            table += "<tr>";

            table += "<td>";
            table += user.getId().toString();
            table += "</td>";

            table += "<td>";
            table += user.getPhone();
            table += "</td>";

            table += "<td>";
            table += user.getFirstName();
            table += "</td>";

            table += "<td>";
            table += user.getSecondName();
            table += "</td>";

            table += "<td>";
            table += user.getThirdName();
            table += "</td>";

            table += "<td>";
            table += user.getPasswordHash();
            table += "</td>";

            table += "<td>";
            table += user.getPrivileges().toString();
            table += "</td>";

            table += "</tr>";
        }
        table += "</table>";
        return template.replace("tablePlace", table);
    }

    public String getAllPrivilegesPage() {
        String table = "";
        table += "<table style='border: 1px solid #777777;'>";
        table += "<tr>";

        table += "<td>";
        table += "name";
        table += "</td>";

        table += "<td>";
        table += "admin";
        table += "</td>";

        table += "<td>";
        table += "description";
        table += "</td>";

        table += "</tr>";
        for(Privilege privilege : privilegesRepository.findAll()) {
            table += "<tr>";

            table += "<td>";
            table += privilege.getName();
            table += "</td>";

            table += "<td>";
            table += privilege.getAdmin();
            table += "</td>";

            table += "<td>";
            table += privilege.getDescription();
            table += "</td>";

            table += "</tr>";
        }
        table += "</table>";
        return template.replace("tablePlace", table);
    }

    public void createAdminAndEmployeePrivileges() {
        Privilege admin = new Privilege();
        admin.setAdmin(true);
        admin.setDescription("It is admin privilege");
        admin.setName("ADMIN");
        privilegesRepository.save(admin);

        Privilege employee = new Privilege();
        employee.setAdmin(false);
        employee.setDescription("It is employee privilege");
        employee.setName("EMPLOYEE");
        privilegesRepository.save(employee);
    }

    public void setAdminUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.getPrivileges().add(privilegesRepository.findById("ADMIN").orElseThrow(BadRequestException::new));
        userRepository.save(user);
    }

    public void setEmployeeUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.getPrivileges().add(privilegesRepository.findById("EMPLOYEE").orElseThrow(BadRequestException::new));
        userRepository.save(user);
    }
}
