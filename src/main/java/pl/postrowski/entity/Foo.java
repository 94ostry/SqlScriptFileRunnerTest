package pl.postrowski.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by postrowski on 2015-11-13.
 */
@Entity
public class Foo
{
    @Id
    @GeneratedValue
    public Long id;

    public String name;
}
