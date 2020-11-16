package com.desafio.desafiointer.controllers;

import com.desafio.desafiointer.dto.SingleDigitDto;
import com.desafio.desafiointer.form.SingleDigitForm;
import com.desafio.desafiointer.models.SingleDigit;
import com.desafio.desafiointer.models.User;
import com.desafio.desafiointer.repository.SingleDigitRepository;
import com.desafio.desafiointer.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/singleDigit")
public class SingleDigitController {
    private final UserRepository userRepository;
    private final SingleDigitRepository singleDigitRepository;

    public SingleDigitController(UserRepository userRepository, SingleDigitRepository singleDigitRepository) {
        this.userRepository = userRepository;
        this.singleDigitRepository = singleDigitRepository;
    }

    /**
     * Calculate the single digit based on some integer.
     * @param sdForm got the values of the String n and integer k, and
     *               a optional userid.
     * @return a long with the value of the expression.
     */
    @PostMapping
    @Transactional
    public long Calculate(@RequestBody @Valid SingleDigitForm sdForm){

        // Cache Single Digit
        // Search on database to find if the parameters was inserted before. if wasn't calculate it.
        // This query is n = sdForm.getN() AND k = sdForm.getK()
        Optional<SingleDigit> existedSg = singleDigitRepository.findByNAndK(sdForm.getN(), sdForm.getK());

        if(existedSg.isPresent())
            return existedSg.get().getResult();

        long result = 0;

        // First loop to cast the String to a new integer
        for (int i = 0; i < sdForm.getN().length() * sdForm.getK(); i++) {
            // Get the position on the String N, its the iterator i in the module of the length of the String.
            int position = (i % sdForm.getN().length());
            result += Character.getNumericValue(sdForm.getN().charAt(position));
        }

        // While the size of the result variable is greater than 1 calculate the single digit.
        while (Long.toString(result).length() > 1) {
            String resultInString = Long.toString(result);
            result = 0;

            for(int i = 0; i < resultInString.length(); i ++){
                result += Character.getNumericValue(resultInString.charAt(i));
            }
        }

        // Create a new SingleDigit object and send the Result to him.
        // Than save it to database cache
        SingleDigit sg = new SingleDigit(sdForm.getN(), sdForm.getK());
        sg.setResult(result);

        singleDigitRepository.save(sg);

        // If the value of the parameter sdForm.getUser() is diff from 0
        // associate it to the user
        if(!sdForm.getUser().isEmpty() && !sdForm.getUser().equals("0")) {
            Optional<User> user = userRepository.findById(Long.parseLong(sdForm.getUser()));
            user.ifPresent(value -> value.getSingleDigits().add(sg));
        }

        return result;
    }

    /**
     * Return a list og Single Digits associate to some user.
     * @param id User id
     * @return a object with a list of single digit Dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<SingleDigitDto>> list(@PathVariable String id) {

        // Validate if the id is valid.
        if(id == null || id.equals(""))
            return ResponseEntity.badRequest().build();

        // Search in database to find some user.
        Optional<User> user = userRepository.findByIdAndDeletedAtIsNull(Long.parseLong(id));

        if(user.isEmpty())
            return ResponseEntity.notFound().build();

        // return the List.
        List<SingleDigit> sg = user.get().getSingleDigits();

        return ResponseEntity.ok(SingleDigitDto.convert(sg));
    }
}
