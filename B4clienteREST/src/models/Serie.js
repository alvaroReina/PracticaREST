
import {isNumber, notBlank} from '../utils/validator'

class Serie {
    
    constructor(title, score, picture, author) {
        this.title = title;
        this.score = score;
        this.picture = picture;
        this.author = author;
    }
    
    setTitle(title) {
        if (notBlank(title)){
            this.title = title;
        } else {
            alert('invalid title', title);
        }
    }
    
    setScore(score) {
        if (isNumber(score)) {
            this.score = score;
        } else {
            alert('invalid score', score);
        }
    }

    setPicture(picture) {
        if (notBlank(picture)) {
            this.picture = picture;
        } else {
            alert('invalid picture', picture)
        }
    }

    setAuthor(author) {
        if (isNumber(author)) {
            this.author = author;
        } else {
            alert('invalid author', author);
        }
    }
}