package ru.ArmyForKing.Mappers;

import javax.annotation.processing.Generated;
import ru.ArmyForKing.Armor.Armor;
import ru.ArmyForKing.Units.Archer;
import ru.ArmyForKing.Units.Peasant;
import ru.ArmyForKing.Weapon.TwoHandedWeapons;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T13:47:43+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class ToArcherMapperImpl implements ToArcherMapper {

    @Override
    public Archer toArcher(Peasant peasant, TwoHandedWeapons weapon, Armor armor) {
        if ( peasant == null && weapon == null && armor == null ) {
            return null;
        }

        Archer archer = new Archer();

        if ( peasant != null ) {
            archer.setFirstName( peasant.getFirstName() );
            archer.setLastName( peasant.getLastName() );
            archer.setPatronymic( peasant.getPatronymic() );
            archer.setGender( peasant.getGender() );
            archer.setAge( peasant.getAge() );
            archer.setHealthAssessment( peasant.getHealthAssessment() );
            archer.setDescription( peasant.getDescription() );
        }
        archer.setWeapon( weapon );
        archer.setArmor( armor );
        archer.setDateOfRequirement( java.time.LocalDateTime.now() );

        setDescription( archer );

        return archer;
    }
}
