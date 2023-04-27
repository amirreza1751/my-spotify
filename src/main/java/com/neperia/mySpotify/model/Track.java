package com.neperia.mySpotify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.Duration;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Track {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String name;

    @Column
    Duration duration;

    @Column
    String path;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnore()
    Album album;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp createdAt;
    @UpdateTimestamp
    Timestamp updatedAt;

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                ", album=" + album +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}