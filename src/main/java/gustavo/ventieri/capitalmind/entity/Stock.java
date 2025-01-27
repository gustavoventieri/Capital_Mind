package gustavo.ventieri.capitalmind.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_Id")
    private Long stockId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "userData", nullable = false) 
    private User userData;

    @Column(name = "createAt", nullable = false)
    @CreationTimestamp
    private Instant createAt;

    @Column(name = "updateAt", nullable = false)
    @UpdateTimestamp
    private Instant updateAt;  
}
