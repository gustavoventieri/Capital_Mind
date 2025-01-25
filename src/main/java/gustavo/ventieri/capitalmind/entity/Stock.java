package gustavo.ventieri.capitalmind.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_stock")
@Data

public class Stock {
    @Id
    @Column(name = "stock_Id")
    private String stockId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "qunantity", nullable = false)
    private String qunatity;

    @ManyToOne
    @JoinColumn(name = "user_id_fk", nullable = false) 
    private User user_id_fk;

    @Column(name = "createAt", nullable = false)
    @CreationTimestamp
    private Instant createAt;

    @Column(name = "updateAt", nullable = false)
    @UpdateTimestamp
    private Instant updateAt;  
}
