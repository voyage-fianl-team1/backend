package com.sparta.matchgi.testData;

import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.PostRepository;
import com.sparta.matchgi.repository.UserRepository;
import com.sparta.matchgi.util.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TestDataRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User testUser1 = new User("nickname2","email2", passwordEncoder.encode("password2"));

        userRepository.save(testUser1);

        createData(testUser1);


    }



    private void createData(User testUser1) {


        for(int i =1; i<100; i++){

            Long id=(long)i;

            String title =getRandomString();

            User user = testUser1;

            String content = getRandomString();

            int viewCount = getRandomInt(100,0);

            int requestCount = 0;

            double lat = getRandomInt(6,33);

            double lng = getRandomInt(7,125);

            int subjectNumber = getRandomInt(6,1);

            String address = getRandomString();

            SubjectEnum subject;

            MatchStatus matchStatus;
            int matchnum=getRandomInt(2,1);
            switch (matchnum){
                case 1: matchStatus=MatchStatus.ONGOING;break;
                default: matchStatus=MatchStatus.MATCHEND;break;
            }


            switch (subjectNumber){
                case 1:
                    subject = SubjectEnum.SOCCER;
                    break;
                case 2:
                    subject = SubjectEnum.BADMINTON;
                    break;
                case 3:
                    subject = SubjectEnum.BASKETBALL;
                    break;
                case 4:
                    subject = SubjectEnum.BILLIARDS;
                    break;
                case 5:
                    subject = SubjectEnum.BOWLING;
                    break;
                default:
                    subject = SubjectEnum.TENNIS;
                    break;

            }
            int day = getRandomInt(10,3);


            LocalDateTime matchDeadline = getRandomDeadline(day+1);

            int owner=-1;



            Post post = new Post(id,user,title,matchDeadline,content,subject,lat,lng,viewCount,requestCount, matchStatus,address,null);

            postRepository.save(post);

        }
    }

    private String getRandomString(){
        Random random = new Random();

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 3;

        String randomString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return randomString;
    }

    private int getRandomInt(int bound, int num) {
        Random random = new Random();

        return random.nextInt(bound) + num;
    }

    private LocalDateTime getRandomDeadline(int day){


        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DATE,day);


        return DateConverter.dateToLocalDateTime(cal.getTime());
    }
}