package ru.PhoneDirectory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.PhoneDirectory.DTO.FullNamePhoneNumbAddress;
import ru.PhoneDirectory.Person;

@Mapper
public interface FullNamePhoneNumbAddressMapper {
    FullNamePhoneNumbAddressMapper INSTANCE = Mappers.getMapper(FullNamePhoneNumbAddressMapper.class);

    FullNamePhoneNumbAddress toFullNamePhoneNumbAddress (Person person);


}
