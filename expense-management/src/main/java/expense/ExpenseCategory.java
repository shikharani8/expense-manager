package expense;

import jakarta.persistence.*;

@Entity
@Table(name="tb_expense_categories")
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
