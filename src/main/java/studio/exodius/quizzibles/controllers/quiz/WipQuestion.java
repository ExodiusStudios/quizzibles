package studio.exodius.quizzibles.controllers.quiz;

import studio.exodius.quizzibles.model.Option;
import studio.exodius.quizzibles.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 * @author Densorius
 * @version 1.0.0
 * @since 20-10-2018
 */
public class WipQuestion extends Question {

    public List<Option> optionsList = new ArrayList<>();

    public WipQuestion(String title) {
        this.title = title;
    }

}
