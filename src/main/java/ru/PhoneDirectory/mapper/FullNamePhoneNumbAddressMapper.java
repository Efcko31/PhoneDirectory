package ru.PhoneDirectory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.PhoneDirectory.dto.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.Person;

@Mapper
public interface FullNamePhoneNumbAddressMapper {
    FullNamePhoneNumbAddressMapper INSTANCE = Mappers.getMapper(FullNamePhoneNumbAddressMapper.class);

    FullNamePhoneNumbAddress toFullNamePhoneNumbAddress (Person person);


}
