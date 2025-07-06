package ru.ArmyForKing.Mappers;

import javax.annotation.processing.Generated;
import ru.ArmyForKing.Armor.Armor;
import ru.ArmyForKing.Horses.Horse;
import ru.ArmyForKing.Units.Cavalry;
import ru.ArmyForKing.Units.Peasant;
import ru.ArmyForKing.Weapon.OneHandedWeapon;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T13:47:44+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class ToCavalryMapperImpl implements ToCavalryMapper {

    @Override
    public Cavalry toCavalry(Peasant peasant, OneHandedWeapon weapon, Armor armor, Horse horse) {
        if ( peasant == null && weapon == null && armor == null && horse == null ) {
            return null;
        }

        Cavalry cavalry = new Cavalry();

        if ( peasant != null ) {
            cavalry.setAge( peasant.getAge() );
            cavalry.setGender( peasant.getGender() );
            cavalry.setFirstName( peasant.getFirstName() );
            cavalry.setLastName( peasant.getLastName() );
            cavalry.setPatronymic( peasant.getPatronymic() );
            cavalry.setHealthAssessment( peasant.getHealthAssessment() );
            cavalry.setDescription( peasant.getDescription() );
        }
        cavalry.setWeapon( weapon );
        cavalry.setArmor( armor );
        cavalry.setWarHorse( horse );
        cavalry.setDateOfRequirement( java.time.LocalDateTime.now() );

        setDescription( cavalry );

        return cavalry;
    }
}
