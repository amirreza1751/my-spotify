package com.neperia.mySpotify.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artist {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String name;

    @Column
    String profilePicture;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    @OrderBy("releaseDate DESC")
    @EqualsAndHashCode.Exclude
    Set<Album> albums;

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", albums=" + albums +
                '}';
    }
}