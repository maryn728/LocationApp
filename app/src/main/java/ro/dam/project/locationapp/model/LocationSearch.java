package ro.dam.project.locationapp.model;

/**
 * Business object for user searches
 */
public class LocationSearch {
    private String ip;
    private CountryInfo countryInfo;
    private User user;

    public LocationSearch() {
    }

    public LocationSearch(String ip, CountryInfo countryInfo, User user) {
        this.ip = ip;
        this.user = user;
        this.countryInfo = countryInfo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    @Override
    public String toString() {
        return "LocationSearch{" +
                ", countryInfo=" + countryInfo +
                ", ip='" + ip + '\'' +
                ", user=" + user +
                '}';
    }
}
