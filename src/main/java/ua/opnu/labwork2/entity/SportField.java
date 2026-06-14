package ua.opnu.labwork2.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sport_fields")
public class SportField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_center_id")
    private SportCenter sportCenter;

    @OneToMany(mappedBy = "sportField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "sport_field_types",
            joinColumns = @JoinColumn(name = "sport_field_id"),
            inverseJoinColumns = @JoinColumn(name = "field_type_id")
    )
    private Set<FieldType> fieldTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public SportCenter getSportCenter() {
        return sportCenter;
    }

    public void setSportCenter(SportCenter sportCenter) {
        this.sportCenter = sportCenter;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<FieldType> getFieldTypes() {
        return fieldTypes;
    }

    public void setFieldTypes(Set<FieldType> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }
}
