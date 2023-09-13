import java.util.ArrayList;
import java.util.Random;

class Character {
    private String name;
    private int hp;
    private int strength;
    private int constitution;
    private int agility;
    private int dexterity;
    private Weapon weapon;
    private Armor armor;
    private ArrayList<Magic> magic;
    private Potion potion;
    private int fireEffectTurns; 
    private int poisonEffectTurns;

    private int healUses;

    public Character(String name, int strength, int constitution, int agility, int dexterity, Weapon weapon,
            Armor armor, Potion potion) {
        this.name = name;
        this.strength = strength;
        this.constitution = constitution;
        this.agility = agility;
        this.dexterity = dexterity;
        this.weapon = weapon;
        this.armor = armor;
        this.potion = potion;
        this.hp = calculateHitPoints();
        this.fireEffectTurns = 0;
        this.poisonEffectTurns = 0;
        this.healUses = 0;
        this.magic = new ArrayList<Magic>();
    }

    private int calculateHitPoints() {
        return rollD6() + rollD6() + rollD6() + constitution;
    }

    public int calculateDamage() {
        int weaponDamage = 0;

        if (weapon.getCategory().equals(Weapon.LONG_SWORD)) {
            weaponDamage = rollD12();
            return weaponDamage + (int) (0.5 * strength) + weapon.getDamageConstant();
        } else if (weapon.getCategory().equals(Weapon.DAGGER)) {
            weaponDamage = rollD6() + rollD6() + rollD4();
            return weaponDamage + (int) (0.33 * dexterity) + weapon.getDamageConstant();
        } else if (weapon.getCategory().equals(Weapon.BOW)) {
            weaponDamage = rollD6() + rollD6() + rollD4();
            return weaponDamage + (int) (0.33 * agility) + weapon.getDamageConstant();
        } else if (weapon.getCategory().equals(Weapon.CROSSBOW)) {
            weaponDamage = rollD12();
            return weaponDamage + (int) (0.5 * dexterity) + weapon.getDamageConstant();
        }
        return 0;
    }

    public int getHeal() {
       if (healUses < 3) {
            healUses++;
            return potion.getHealing();
        } else {
            return 0;
        }
    }

    public Magic[] getMagics() {
        return magic.toArray(new Magic[magic.size()]);
    }

    public String getMagic(int index){
        return magic.get(index).getMagicCategory();
    }

    public void castMagic(Character target, int index, String magicCategory){
        magic.get(index).cast(target, magicCategory);
    }

    public void addMagic(Magic magic) {
        this.magic.add(magic);
    }

    public void heal(int getHeal) {
        hp += getHeal();
    }

    private int rollD12() {
        Random rand = new Random();
        return rand.nextInt(12) + 1;
    }

    private int rollD6() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    private int rollD4() {
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    public int getHitPoints() {
        return hp;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getTotalDamage() {
        int weaponDamage = calculateDamage();
        return weaponDamage;
    }

	public void reduceAgility(int amount) {
        agility -= amount;
    }

    public void reduceDexterity(int amount) {
        dexterity -= amount;
    }

    public void reduceConstitution(int amount) {
        constitution -= amount;
    }

	public void reduceStrength(int amount) {
        strength -= amount;
    }

    public int getFireEffectTurns() {
        return fireEffectTurns;
    }

    public int setFireEffectTurns() {
        return fireEffectTurns = 3;
    }

    public int getPoisonEffectTurns() {
        return poisonEffectTurns;
    }

    public int setPoisonEffectTurns() {
        return poisonEffectTurns = 4;
    }

    public void applyFireEffect() {
            int fireTurnDamage = 3;
            receiveDamage(fireTurnDamage);
            fireEffectTurns--;
    }

    public void applyPoisonEffect() {
            int poisonTurnDamage = 2;
            receiveDamage(poisonTurnDamage);
            poisonEffectTurns--;
    }

    @Override
    public String toString() {
        return name + "\nHP: " + getHitPoints() + "\nStrength: " + getStrength() + "\nConstitution: " + getConstitution()
                + "\nAgility: " + getAgility() + "\nDexterity: " + getDexterity() + "\nWeapon: " + getWeapon().toString()
                + "\nArmor: " + getArmor().toString() + "\nMagic: " + getMagics();
    }
}