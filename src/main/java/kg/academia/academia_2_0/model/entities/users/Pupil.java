package kg.academia.academia_2_0.model.entities.users;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pupil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    private UserData userData;
    private Integer balance;
    @ManyToOne
    private Branch branch;


    public void balanceUp(int amount){
        this.balance += amount;
    }

    public void balanceDown(int amount){
        this.balance -= amount;
        if(balance < 0)
            this.userData.setActive(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pupil pupil = (Pupil) o;
        return id.equals(pupil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
