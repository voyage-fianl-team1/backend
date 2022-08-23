package com.sparta.matchgi.testData;

import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.*;
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

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    private final ImageRepository imageRepository;

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

            double lat;

            double lng;

            int latnum=getRandomInt(3,1);
            switch (latnum){
                case 1:lat=35.8923285861355;lng=128.61556339142595;break;
                case 2:lat=35.882713157875514;lng=128.61357418841052;break;
                default:lat=35.60577161640945;lng=128.56142639705237;break;
            }


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

            ImgUrl imgUrl =new ImgUrl(post,"2f133003-1c1f-4329-a02b-8619c89e385a_KakaoTalk_Image_2022-07-26-01-35-18.png","https://yougeun-bucket.s3.ap-northeast-2.amazonaws.com/2f133003-1c1f-4329-a02b-8619c89e385a_KakaoTalk_Image_2022-07-26-01-35-18.png");

            imageRepository.save(imgUrl);


            Room room = new Room(post.getId(),user,post);

            roomRepository.save(room);

            UserRoom userRoom = new UserRoom(user,room);

            userRoomRepository.save(userRoom);


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