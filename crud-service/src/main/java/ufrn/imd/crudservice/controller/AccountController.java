package ufrn.imd.crudservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.crudservice.client.BankServiceClient;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private BankServiceClient bankServiceClient;

    // Endpoint Welcome
    @GetMapping("/welcome")
    public String welcome() {
        return bankServiceClient.welcome();
    }

    // Endpoint Sum
    @GetMapping("/sum/{x}/{y}")
    public double sum(@PathVariable double x, @PathVariable double y){ return bankServiceClient.sum(x, y);}

    // Endpoint Multiplication
    @GetMapping("/multiplication/{x}/{y}")
    public double multiplication(@PathVariable double x, @PathVariable double y){ return bankServiceClient.sum(x, y);}
}