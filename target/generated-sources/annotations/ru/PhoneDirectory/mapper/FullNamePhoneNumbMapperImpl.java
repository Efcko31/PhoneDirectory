package ru.PhoneDirectory.mapper;

import javax.annotation.processing.Generated;
import ru.PhoneDirectory.Person;
import ru.PhoneDirectory.dto.FullNamePhoneNumb;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-21T14:13:58+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
public class FullNamePhoneNumbMapperImpl implements FullNamePhoneNumbMapper {

    @Override
    public FullNamePhoneNumb toFullNamePhoneNumb(Person person) {
        if ( person == null ) {
            return null;
        }

        FullNamePhoneNumb fullNamePhoneNumb = new FullNamePhoneNumb();

        fullNamePhoneNumb.setPhoneNumber( person.getPhoneNumber() );
        fullNamePhoneNumb.setFirstName( person.getFirstName() );
        fullNamePhoneNumb.setLastName( person.getLastName() );
        fullNamePhoneNumb.setPatronymic( person.getPatronymic() );

        return fullNamePhoneNumb;
    }
}
