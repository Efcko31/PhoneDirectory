package ru.ArmyForKing.Mappers;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import ru.ArmyForKing.Armor.Armor;
import ru.ArmyForKing.Units.Infantryman;
import ru.ArmyForKing.Units.Peasant;
import ru.ArmyForKing.Weapon.OneHandedWeapon;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T13:47:44+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class ToInfantrymanMapperImpl implements ToInfantrymanMapper {

    @Override
    public Infantryman toInfantryman(Peasant peasant, OneHandedWeapon weapon, Armor armor) {
        if ( peasant == null && weapon == null && armor == null ) {
            return null;
        }

        Infantryman infantryman = new Infantryman();

        if ( peasant != null ) {
            infantryman.setFirstName( peasant.getFirstName() );
            infantryman.setLastName( peasant.getLastName() );
            infantryman.setPatronymic( peasant.getPatronymic() );
            infantryman.setGender( peasant.getGender() );
            infantryman.setAge( peasant.getAge() );
            infantryman.setHealthAssessment( peasant.getHealthAssessment() );
            infantryman.setDescription( peasant.getDescription() );
        }
        infantryman.setWeapon( weapon );
        infantryman.setArmor( armor );
        infantryman.setDateOfRequirement( LocalDateTime.now() );

        setDescription( infantryman );

        return infantryman;
    }
}
