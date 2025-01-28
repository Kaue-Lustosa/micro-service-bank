package ufrn.imd.compositeservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {
    @GetMapping("/public/welcome")
    public String welcome() {
        return "Welcome to Fall Bank!";
    }

    @PostMapping("/public/sum/{x}/{y}")
    public double sum(@PathVariable double x, @PathVariable double y){
        return x + y;
    }

    @PostMapping("/public/multiplication/{x}/{y}")
    public double multiplication(@PathVariable double x, @PathVariable double y){
        return x * y;
    }
}