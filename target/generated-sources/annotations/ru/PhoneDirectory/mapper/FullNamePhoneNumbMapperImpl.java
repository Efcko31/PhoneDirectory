package ru.PhoneDirectory.mapper;

import javax.annotation.processing.Generated;
import ru.PhoneDirectory.DTO.FullNamePhoneNumb;
import ru.PhoneDirectory.Person;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T07:53:58+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
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
