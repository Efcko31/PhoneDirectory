package ru.PhoneDirectory.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.PhoneDirectory.Person;

@Mapper
public interface ReplacedUserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
            void updatePersonFromDto(@MappingTarget Person entity, Person newPersonData);


    ReplacedUserMapper INSTANCE = Mappers.getMapper(ReplacedUserMapper.class);
    Person toReplacedUser (Person person);
}
