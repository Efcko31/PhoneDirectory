package ru.StreamApi.Exersises;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-01T15:31:27+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class Ex7MapperImpl implements Ex7Mapper {

    @Override
    public DTOForEx7 toDTOForEx7(StudentsForEx7 studentsForEx7, double averageScore) {
        if ( studentsForEx7 == null ) {
            return null;
        }

        DTOForEx7 dTOForEx7 = new DTOForEx7();

        if ( studentsForEx7 != null ) {
            dTOForEx7.setName( studentsForEx7.getName() );
        }
        dTOForEx7.setAverageScore( averageScore );

        return dTOForEx7;
    }
}
