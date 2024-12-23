package com.toyhe.app.Auth.Model;

import com.toyhe.app.Auth.Dtos.Requests.AccessOperation;
import jakarta.persistence.*;
import lombok.*;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessRights {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user ;

    @ManyToOne
    @JoinColumn(name =  "model_id")
    private  Model model ;

   // @Enumerated(EnumType.STRING)
   // @Column(nullable = false)
    //private  Operations operation ;
    private  boolean accessRead ;
    private  boolean accessWrite ;
    private  boolean accessUpdate ;
    private  boolean accessDelete ;


}
