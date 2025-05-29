package com.XCLONE.Budget_tracker.Controllers;

import com.XCLONE.Budget_tracker.Entity.Transaction;
import com.XCLONE.Budget_tracker.Entity.Users;
import com.XCLONE.Budget_tracker.Repository.TransactionRepository;
import com.XCLONE.Budget_tracker.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepo;
    private final UsersRepository userRepo;

    // 🔹 Get all transactions for logged-in user
    @GetMapping
    public List<Transaction> getUserTransactions(Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        return transactionRepo.findByUserId(user.getId());
    }

    // 🔹 Add a new transaction
    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction tx, Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        tx.setUser(user);
        return transactionRepo.save(tx);
    }

    // 🔹 Update transaction
    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction updatedTx, Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        Transaction tx = transactionRepo.findById(id).orElseThrow();
        if (!tx.getUser().getId().equals(user.getId())) throw new RuntimeException("Unauthorized");

        tx.setTitle(updatedTx.getTitle());
        tx.setAmount(updatedTx.getAmount());
        tx.setDescription(updatedTx.getDescription());
        tx.setCategory(updatedTx.getCategory());
        tx.setDate(updatedTx.getDate());

        return transactionRepo.save(tx);
    }

    // 🔹 Delete transaction
    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id, Authentication auth) {
        Users user = userRepo.findByEmail(auth.getName()).orElseThrow();
        Transaction tx = transactionRepo.findById(id).orElseThrow();
        if (!tx.getUser().getId().equals(user.getId())) throw new RuntimeException("Unauthorized");

        transactionRepo.delete(tx);
        return "Transaction deleted";
    }
}
