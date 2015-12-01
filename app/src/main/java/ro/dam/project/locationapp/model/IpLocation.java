package ro.dam.project.locationapp.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

/**
 * To be used for mapping JSON response from "https://freegeoip.net"
 */
public class IpLocation implements Serializable {

    private String ip;
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String regionName;
    private String city;
    private String zipCode;
    private String timeZone;
    private double latitude;
    private double longitude;
    private int metroCode;

    @JsonGetter("ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonGetter("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @JsonGetter("country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonGetter("region_code")
    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @JsonGetter("region_name")
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @JsonGetter("city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonGetter("zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @JsonGetter("time_zone")
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @JsonGetter("latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonGetter("longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @JsonGetter("metro_code")
    public int getMetroCode() {
        return metroCode;
    }

    public void setMetroCode(int metroCode) {
        this.metroCode = metroCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IpLocation)) return false;

        IpLocation that = (IpLocation) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (metroCode != that.metroCode) return false;
        if (!ip.equals(that.ip)) return false;
        if (!countryCode.equals(that.countryCode)) return false;
        if (!countryName.equals(that.countryName)) return false;
        if (!regionCode.equals(that.regionCode)) return false;
        if (!regionName.equals(that.regionName)) return false;
        if (!city.equals(that.city)) return false;
        return !zipCode.equals(that.zipCode) ? false : timeZone.equals(that.timeZone);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = ip.hashCode();
        result = 31 * result + countryCode.hashCode();
        result = 31 * result + countryName.hashCode();
        result = 31 * result + regionCode.hashCode();
        result = 31 * result + regionName.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + timeZone.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + metroCode;
        return result;
    }

    @Override
    public String toString() {
        return "IpLocation{" +
                "ip='" + ip + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", regionName='" + regionName + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", metroCode=" + metroCode +
                '}';
    }
}
