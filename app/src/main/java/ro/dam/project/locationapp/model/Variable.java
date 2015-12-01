package ro.dam.project.locationapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;

/**
 * To be used for mapping JSON response when requesting a statistical variable
 */
public class Variable implements Parcelable {

    private int id;
    private String name;
    private String shortName;
    private String units;
    private String timePeriod;
    private String fileName;
    private String unepPriority;
    private String geoTheme;
    private String geoSubTheme;
    private String dataSetType;

    public Variable(int id, String name, String shortName, String units, String timePeriod, String fileName,
                    String unepPriority, String geoTheme, String geoSubTheme, String dataSetType) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.units = units;
        this.timePeriod = timePeriod;
        this.fileName = fileName;
        this.unepPriority = unepPriority;
        this.geoTheme = geoTheme;
        this.geoSubTheme = geoSubTheme;
        this.dataSetType = dataSetType;
    }

    public Variable() {
    }

    @JsonGetter("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("name_short")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonGetter("units")
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @JsonGetter("time_period")
    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    @JsonGetter("file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonGetter("unep_priority")
    public String getUnepPriority() {
        return unepPriority;
    }

    public void setUnepPriority(String unepPriority) {
        this.unepPriority = unepPriority;
    }

    @JsonGetter("geo_theme")
    public String getGeoTheme() {
        return geoTheme;
    }

    public void setGeoTheme(String geoTheme) {
        this.geoTheme = geoTheme;
    }

    @JsonGetter("geo_subtheme")
    public String getGeoSubTheme() {
        return geoSubTheme;
    }

    public void setGeoSubTheme(String geoSubTheme) {
        this.geoSubTheme = geoSubTheme;
    }

    @JsonGetter("data_set_type")
    public String getDataSetType() {
        return dataSetType;
    }

    public void setDataSetType(String dataSetType) {
        this.dataSetType = dataSetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;

        Variable variable = (Variable) o;

        if (id != variable.id) return false;
        if (!name.equals(variable.name)) return false;
        if (shortName != null ? !shortName.equals(variable.shortName) : variable.shortName != null)
            return false;
        if (!units.equals(variable.units)) return false;
        if (!(timePeriod == variable.timePeriod)) return false;
        if (!fileName.equals(variable.fileName)) return false;
        if (!unepPriority.equals(variable.unepPriority)) return false;
        if (!geoTheme.equals(variable.geoTheme)) return false;
        return !geoSubTheme.equals(variable.geoSubTheme) ? false : dataSetType.equals(variable.dataSetType);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + units.hashCode();
        result = 31 * result + timePeriod.hashCode();
        result = 31 * result + fileName.hashCode();
        result = 31 * result + unepPriority.hashCode();
        result = 31 * result + geoTheme.hashCode();
        result = 31 * result + geoSubTheme.hashCode();
        result = 31 * result + dataSetType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", units='" + units + '\'' +
                ", timePeriod='" + timePeriod + '\'' +
                ", fileName='" + fileName + '\'' +
                ", unepPriority='" + unepPriority + '\'' +
                ", geoTheme='" + geoTheme + '\'' +
                ", geoSubTheme='" + geoSubTheme + '\'' +
                ", dataSetType='" + dataSetType + '\'' +
                '}';
    }

    protected Variable(Parcel in) {
        id = in.readInt();
        name = in.readString();
        shortName = in.readString();
        units = in.readString();
        timePeriod = in.readString();
        fileName = in.readString();
        unepPriority = in.readString();
        geoTheme = in.readString();
        geoSubTheme = in.readString();
        dataSetType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(shortName);
        dest.writeString(units);
        dest.writeString(timePeriod);
        dest.writeString(fileName);
        dest.writeString(unepPriority);
        dest.writeString(geoTheme);
        dest.writeString(geoSubTheme);
        dest.writeString(dataSetType);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Variable> CREATOR = new Parcelable.Creator<Variable>() {
        @Override
        public Variable createFromParcel(Parcel in) {
            return new Variable(in);
        }

        @Override
        public Variable[] newArray(int size) {
            return new Variable[size];
        }
    };
}