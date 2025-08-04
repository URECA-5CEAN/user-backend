//package com.ureca.ocean.jjh.DataFaker;
//
//import com.ureca.ocean.jjh.user.entity.User;
//import com.ureca.ocean.jjh.user.entity.enums.Gender;
//import com.ureca.ocean.jjh.user.entity.enums.Membership;
//import com.ureca.ocean.jjh.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.datafaker.Faker;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Locale;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DummyUser implements CommandLineRunner {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        Faker faker = new Faker(Locale.KOREA);
//        log.info("🧪 Dummy User 생성 시작...");
//        String[] animals = {
//                "고양이", "강아지", "여우", "곰", "사자", "늑대", "토끼", "호랑이", "다람쥐", "펭귄",
//                "너구리", "하마", "코끼리", "기린", "캥거루", "수달", "하늘다람쥐", "표범", "청설모", "두더지"
//        };
//
//        for (int i = 1; i <= 50; i++) {
//            User user = User.builder()
//                    .name(faker.name().fullName().replace(" ", ""))
//                    .nickname(faker.color().name()+" "+animals[faker.random().nextInt(animals.length)])
//                    .email("user" + i + "@ureca.com")
//                    .password(passwordEncoder.encode("1234"))
//                    .gender(faker.options().option(Gender.values()))
//                    .address(faker.address().fullAddress())
//                    .membership(faker.options().option(Membership.values()))
//                    .title(null)
//                    .build();
//
//            userRepository.save(user);
//            log.info("Saved: {}", user);
//        }
//        log.info("✅ Dummy User 10명 출력 완료");
//    }
//}
