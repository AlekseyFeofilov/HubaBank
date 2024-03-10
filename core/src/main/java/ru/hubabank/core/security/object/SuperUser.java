package ru.hubabank.core.security.object;

import java.security.Principal;

public class SuperUser implements Principal {

    @Override
    public String getName() {
        return "root";
    }
}
