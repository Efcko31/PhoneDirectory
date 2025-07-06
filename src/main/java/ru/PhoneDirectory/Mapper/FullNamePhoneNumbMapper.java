package ru.PhoneDirectory.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.PhoneDirectory.DTO.FullNamePhoneNumb;
import ru.PhoneDirectory.Person;

@Mapper
public interface FullNamePhoneNumbMapper {
    FullNamePhoneNumbMapper INSTANCE = Mappers.getMapper(FullNamePhoneNumbMapper.class);

    FullNamePhoneNumb toFullNamePhoneNumb(Person person);
}
