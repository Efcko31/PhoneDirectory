package ru.PhoneDirectory.mapper;

import javax.annotation.processing.Generated;
import ru.PhoneDirectory.DTO.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.Person;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T19:30:16+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class FullNamePhoneNumbAddressMapperImpl implements FullNamePhoneNumbAddressMapper {

    @Override
    public FullNamePhoneNumbAddress toFullNamePhoneNumbAddress(Person person) {
        if ( person == null ) {
            return null;
        }

        FullNamePhoneNumbAddress fullNamePhoneNumbAddress = new FullNamePhoneNumbAddress();

        fullNamePhoneNumbAddress.setPhoneNumber( person.getPhoneNumber() );
        fullNamePhoneNumbAddress.setFirstName( person.getFirstName() );
        fullNamePhoneNumbAddress.setLastName( person.getLastName() );
        fullNamePhoneNumbAddress.setPatronymic( person.getPatronymic() );
        fullNamePhoneNumbAddress.setCityOfResidence( person.getCityOfResidence() );
        fullNamePhoneNumbAddress.setAddress( person.getAddress() );

        return fullNamePhoneNumbAddress;
    }
}
