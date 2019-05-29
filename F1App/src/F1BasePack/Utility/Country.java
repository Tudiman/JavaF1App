package F1BasePack.Utility;

import java.util.ArrayList;

public class Country {

    private static ArrayList<Country> currentList = new ArrayList<>();

    private String name;
    private String referralName;
    private String continent = "Other";

    public Country() {
    }

    public Country(String name, String referralName) {
        this.name = name;
        this.referralName = referralName;
    }

    public Country(String name, String referralName, String continent) {
        this.name = name;
        this.referralName = referralName;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public String getReferralName() {
        return referralName;
    }

    public String getContinent() {
        return continent;
    }

    public static ArrayList<Country> getCurrentList() {
        return currentList;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInfo() {
        return "Country{" +
                "name='" + name + '\'' +
                ", referralName='" + referralName + '\'' +
                ", continent='" + continent + '\'' +
                '}';
    }

    public static void setCurrentList(ArrayList<Country> currentList) {
        Country.currentList = currentList;
    }

    public static Country parseLine(String country) {

        String[] values = country.split(",");
        return new Country(values[0],values[1],values[2]);
    }

    public static Country convertToCountry(String name) {

        for(Country country:currentList) {
            if(country.getName().equals(name))
                return country;
        }
        throw new Error("Country not found");
    }
}
