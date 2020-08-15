package net.its.testtask.rooms.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Model object that represents a lamp.
 */
@Embeddable
@Data
public class Lamp {

    @Column(name = "is_on")
    private boolean isOn;
}
