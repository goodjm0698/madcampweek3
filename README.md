# DAIRY
> Letter sharing Application for participant of MadCamp (centering on inner join and OAuth security)

## Development Information
### ⌛️ Dev. Duration
13, July, 2023 ~ 19, July, 2023

### 👫 The Team
- Jaemin Kim(Korea University, Computer Science & Engineering)
- Yeonguk Jang (KAIST, Computer Science)

### 🌏 Dev. Environment
- ```Java 11```
- Spring Boot
- PyTorch
- Flask
- Flutter

 ## Motivation
 motivation 🥰

 ## AI
We have created a model that analyzes emotions in diary entries and classifies them into seven categories: [불안, 당황, 분노, 슬픔, 중립, 행복, 혐오].
 
 ### Dataset
 감성대화 말뭉치 (https://aihub.or.kr/aidata/7978)
 
 ### Model 
 PyTorch 라이브러리와 KoBERT 모델을 이용한 감정 분류
 
 ### Data Preprocessing
 - 텍스트는 Transformer기반의 ko-Bert모델에서 주어지는 토큰화함수를 이용하여 벡터화하여 활용
 - 7가지 감정에 대한 Multi-category 분류


 
 ## Main Function
 ### Section 1. User Handling
 - Login
 - Sign up
 - Sign up with kakao account
 - Login with kakao account

![login](/photos/login.jpeg)
![signup](/photos/signup.jpeg)
![wrong](/photos/wrong.jpeg)
![invalid](/photos/invalid.jpeg)

### Section 2. My Tree
- Read letters user received
- Display Tree with fruits(letter)

<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/a8b3c422-e923-4e1b-8690-b378848165c2" width="500">
<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/04deb431-8268-44fb-bf1a-65159f8bea71" width="500">
<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/61f7f25d-3500-4a13-bc3a-4849f7de0d3b" width="500">
<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/7f9a80fd-6c2e-446f-8243-b1d9c28f1ddc" width="500">



### Section 3. Write Letter to other user
- Show list of other users
- Write letter to selected user

<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/223322f7-35e6-4fe7-a952-50d4060ce996" width="500">
<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/1f2a72f1-3e77-4a0b-b475-6dc4fb2056e4" width="500">

### Section 4. Shared letters
- List existing groups
- Write group letter to selected group
- Read letters written by other users

<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/ac3408ad-32f8-4543-a4eb-13f9d8438040" width="500">
<img src="https://github.com/goodjm0698/madcampweek3/assets/64831392/0f1ca37d-0bd6-4f84-85c9-9fad97df1df8" width="500">
