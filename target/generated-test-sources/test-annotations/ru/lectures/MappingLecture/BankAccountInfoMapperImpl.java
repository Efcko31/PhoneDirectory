package ru.lectures.MappingLecture;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-18T07:57:34+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class BankAccountInfoMapperImpl implements BankAccountInfoMapper {

    @Override
    public ShortInfo toShortInfo(ClientBankAccount person) {
        if ( person == null ) {
            return null;
        }

        ShortInfo shortInfo = new ShortInfo();

        shortInfo.setResidentOf( person.getCountry() );
        shortInfo.setFirstName( person.getFirstName() );
        shortInfo.setLastName( person.getLastName() );
        shortInfo.setPatronymic( person.getPatronymic() );
        shortInfo.setAmount( person.getAmount() );

        return shortInfo;
    }
}
