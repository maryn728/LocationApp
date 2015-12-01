package ro.dam.project.locationapp.model;

/**
 * Business object for storing information regarding a certain country
 */
public class CountryInfo {
    private String countryName;
    private String countryFlag;
    private String capital;
    private String offLanguages;
    private long population;
    private String description;

    public CountryInfo() {
    }

    public CountryInfo(String countryName) {
        this.countryName = countryName;
        this.countryFlag = null;
        this.capital = null;
        this.offLanguages = null;
        this.population = -1;
        this.description = null;
    }

    public CountryInfo(String countryName, String countryFlag, String capital, String offLanguages, long population, String description) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;
        this.capital = capital;
        this.offLanguages = offLanguages;
        this.population = population;
        this.description = description;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getOffLanguages() {
        return offLanguages;
    }

    public void setOffLanguages(String offLanguages) {
        this.offLanguages = offLanguages;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "CAPITAL: \n" + capital + "\n\n" +
                "OFFICIAL LANGUAGES: \n" + offLanguages + "\n\n" +
                "POPULATION: \n" + population + "\n\n" +
                "DESCRIPTION: \n" + description
                ;
    }
}
