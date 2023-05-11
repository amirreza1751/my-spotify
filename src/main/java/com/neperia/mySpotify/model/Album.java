package com.neperia.mySpotify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neperia.mySpotify.enums.Genre;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String title;

    @Column
    String cover;

    @Column
    Timestamp releaseDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore()
    Artist artist;

    @Column
    Genre genre;

    @OneToMany(mappedBy = "album", cascade = {CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    Set<Track> tracks;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdAt;

    @UpdateTimestamp
    Timestamp updatedAt;

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", releaseDate=" + releaseDate +
                ", artist=" + artist +
                ", genre=" + genre +
                ", tracks=" + tracks +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}