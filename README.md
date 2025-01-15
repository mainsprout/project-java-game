# 자바 기말 프로젝트 보고서

2024-1 자바기반응용프로그래밍 50/50 을 받은 프로젝트입니다.

## I. 설명

- 분류 : 창작 게임
- 게임 이름 : the Last Stroke
- 게임 줄거리 : 20××년. 그림을 그리는 로봇인 ‘Artist’가 그림을 그리고, 그 그림을 주위에게 전달하여 행복을 나누는 이야기이다. 배터리가 꺼지기 전까지 행복을 전달하는데 성공하면 해피엔딩. 실패하면 GAME OVER이다.
- 플레이타임 : 2분 30초 + **α**

## II. 초기 화면(InitScreen.java)

프로젝트를 실행시키면 뜨는 초기화면은 아래와 같다.

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/452867f8-3d4d-45ac-875a-175f106d26f0/Untitled.png)

초기화면에는 총 2가지 버튼이 있다.

1. 게임시작
    
    게임 시작 버튼을 누르면 메인 게임화면으로 이동한다.
    
    ```java
    //startButton
    startButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dispose(); // 현재 component를 제거
                    clip.close(); // 음악 끄기
                    new MainScreen(); // Main 화면으로 전환
                }
                @Override
                public void mouseEntered(MouseEvent e){
                    clickSound.clickSound(); // 효과음
                }
            });
    ```
    
2. 종료
    
    종료 버튼을 누르면 프로젝트가 종료된다.
    
    ```java
    //quitButton
    quitButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0); // 프로젝트 종료
                }
                @Override
                public void mouseEntered(MouseEvent e){
                    clickSound.clickSound(); // 효과음
                }
            });
    ```
    

## III. 메인 게임 화면 (MainScreen.java)

### 1. 주인공(Artist)

이 게임의 주인공(Artist)이다. 

오른쪽 방향키 혹은 D키를 누르면 오른쪽으로 걷는 모션이 실행된다. 캐릭터의 위치는 중앙부에 고정한 채, background를 왼쪽으로 움직여서 캐릭터가 움직이는 듯한 효과를 주었다.

```java
addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!enableAdapter) return; // Drawing 중일때는 비활성화

                int keyCode = e.getKeyCode();

                switch (keyCode){
                    case KeyEvent.VK_RIGHT,VK_D:
                        if(start){
                            playWalkingSound(); // 걷는 효과음
                        }
                        k++; // k는 걷는 속도를 조절하기 위한 변수
                        if(k % motionSpeed == 0){ //k를 motionSpeed로 나누었을 때 나머지가 0이면 
                            k = 0; // k 초기화
                            artist.setIcon(walk_right[(index++)%7]); //artist 모션 바꾸기
                            if(artist.getX() <= getWidth()/3){ // 캐릭터가 화면 중앙에 오기 전까지는 캐릭터가 움직임
                                artist.setLocation(artist.getX()+speed, artist.getY());
                            }else if(streetBackground.getX() >= -4500){ 
                            // 화면 밖으로 튀어나가는 것을 막기 위함
                                streetBackground.setLocation(streetBackground.getX()-speed, streetBackground.getY());
                                effectBackground.setLocation(effectBackground.getX()-speed, effectBackground.getY());
                                canvas.setLocation(canvas.getX()-speed, canvas.getY());
                                cat.setLocation(streetBackground.getX()+2700, cat.getY());
                                man.setLocation(streetBackground.getX()+5200, man.getY());
                                album.setLocation(streetBackground.getX()+50, album.getY());
                                message.setLocation(man.getX()+10, message.getY());
                                catmessage.setLocation(cat.getX()+10, 380);
		                            //모든 배경 object들 위치 이동
                            }
                        }

                        break;

...
```

왼쪽 방향키 혹은 A키를 누르면 왼쪽으로 걷는 모션이 실행되며 상세한 내용은 오른쪽과 같다.

### 2. 그림판

![마우스가 그림판 위에 올라가기 전](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/2db2ad06-9312-4618-920d-6067949acbfc/Untitled.png)

마우스가 그림판 위에 올라가기 전

![마우스가 그림판 위로 올라간 후](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/80bbffc6-e98e-4505-8fc8-f07cd5a6298e/Untitled.png)

마우스가 그림판 위로 올라간 후

마우스로 그림판을 클릭하면, 그림을 그릴 수 있다. 

```java
 canvas.setIcon(new ImageIcon("images/component/canvas.png"));
        canvas.setBounds(1040, 225, 261, 273);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!enableAdapter) return; // 이미 Drawing 중인 상황
                int result = JOptionPane.showConfirmDialog(null,
                        "무언가 떠올려볼까?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    canvas.setIcon(new ImageIcon("images/component/canvas.png"));
                    // background 및 artist를 정해진 위치로 조정
                    streetBackground.setLocation(-930, streetBackground.getY());
                    effectBackground.setLocation(-930, effectBackground.getY());
                    artist.setLocation(100, artist.getY());
                    canvas.setLocation(110, canvas.getY());
                    enableAdapter = false; // Drawing 중임을 표시
                    drawPanel.setVisible(true); // 그림을 그리기 위한 panel
                    gallery.setVisible(false); // gallery 버튼 비활성화
                    timer.stop(); // 그림을 그리는동안은 시간이 정지됨
                    temp.setIcon(null); // canvas 위에 동일한 그림을 그려주기 위함
                    temp.setVisible(true);
                    album.setVisible(false); // album 비활성화
                }

            }
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/384e64d8-b808-45ad-bab3-4ecd2dcaba9f/Untitled.png)

- `penButton` : canvas에 그림을 그릴 수 있는 도구이다. 먼저 펜을 선택하고, 색깔을 선택하면 그림을 그릴 수 있다. 둘 중 하나라도 선택되지 않았더라면 경고 창이 뜬다.
    - `ToolButtonListener` 를 ActionListener로 가진다. ToolButton은 button의 ToolTipText를 type으로 설정해준다.
- `eraseButton` : canvas에 그려진 그림을 지울 수 있는 도구이다.
    - `ToolButtonListener` 를 ActionListener로 가진다.
- `allPaint` : canvas의 전체 색깔을 덮을 수 있는 페인트통이다. 색깔을 선택한 뒤 이 버튼을 누르면 해당 색깔로 canvas가 덮인다.
    
    ```java
    allPaint.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setImageBackground(bufferedImage, ImagePanel.getColor());
                    // bufferedImage의 배경을 ImagePanel의 현재 색상으로 설정한다.
                    imagePanel.repaint(); // imagePanel을 다시 그려 업데이트 된 내용 반영
                    ImagePanel.upDate(); // 오른쪽 캐릭터가 그리는 캔버스도 upDate 해준다
                }
            });
    ```
    
- `allErase` : canvas를 초기화한다.
- `sizeBar` : pan 혹은 erase의 크기를 조절한다. JSilder로 구현되어있다.
    - `SizeSilderListener`를 ChangeListener로 가진다. 현재 JSilder의 값을 setStrokeSize로 넘겨주어서 크기를 조절한다.
- `colorPalette` : 기본적으로 7가지의 색깔이 JButton으로 추천하고 있다. 또한 moreColor 버튼을 눌러서 더 많은 다른 색을 고를 수도 있다.
    - moreColor 버튼을 누르면 JColorChooser을 화면에 띄워주고, 이로 인해 색을 바꿀 수 있다.
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/0815c71a-e6b0-4e32-b308-ec83026b849f/Untitled.png)
    

- `SAVE` : 현재까지 그린 그림을 저장할 수 있다. 그림은 게임 내 gallery에 저장된다.
- `X` : DrawScreen을 보이지 않게 한다. (drawing 종료)

주요 도구는 위와 같다. 마우스로 그림을 그리면 왼쪽 부분에서 캐릭터가 실제 그림을 그리는 것처럼 움직인다. 또한, 자신이 그린 그림이 동일하게 표시되는 것을 볼 수 있다.

이제 pen/erase기능이 어떻게 작동하는지 살펴보자.

```java
bufferedImage = new BufferedImage(520, 600, BufferedImage.TYPE_INT_ARGB);
        setImageBackground(bufferedImage, Color.white);
        // BufferImage 객체를 생성한다. 배경색은 흰색으로 지정한다.

        imagePanel = new ImagePanel(bufferedImage);
        //ImagePanel에 생성항 bufferedImage 전달
        imagePanel.setBounds(30, 60, 520, 600);
        add(imagePanel);
```

```java
// ImagePanel에는 setupListeners가 Listener로 추가되어있다.
addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            // 마우스가 눌렸을 때의 좌표를 지정
                firstPointer.setLocation(e.getX(), e.getY());
                secondPointer.setLocation(e.getX(), e.getY());
                artistDrawing(); //캐릭터가 움직이게 하는 함수
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                artistDrawing();//캐릭터가 움직이게 하는 함수
                upDate(); //화면 update
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(type == null){
                //type이 지정되지 않았더라면 안내창
                    JOptionPane.showMessageDialog(null, "도구를 선택해주세요",
                            "안내", JOptionPane.INFORMATION_MESSAGE);
                }
                if ("펜".equals(type) || "지우개".equals(type)) {
                //type이 펜 혹은 지우개라면 secondPointer를 지정하고 updatePaint()
                    secondPointer.setLocation(e.getX(), e.getY());
                    updatePaint();
                    firstPointer.setLocation(secondPointer);
                    //secondPaint를 다시 firstPoint로 지정한다.
                }
            }
        });
}
```

```java
private void updatePaint() {
        Graphics2D g = bufferedImage.createGraphics();
        // 그리기 작업을 하기 위한 Graphics2D g 생성
        if ("펜".equals(type)) {
            if(color == null){
                JOptionPane.showMessageDialog(null, "색깔을 선택해주세요",
                        "안내", JOptionPane.INFORMATION_MESSAGE);
            }
            g.setColor(color); //g의 color를 현재 선택된 color로
            g.setStroke(new BasicStroke(strokeSize)); // g의 굵기를 현재 선택된 굵기로
            g.drawLine(firstPointer.x, firstPointer.y, secondPointer.x, secondPointer.y);
            //firstPoint와 secondPoint를 이용하여 그림을 그린다.
        }
        else if ("지우개".equals(type)){
            g.setColor(Color.white); // g의 color를 하얀색으로
            g.setStroke(new BasicStroke(strokeSize)); // g의 굵기를 현재 선택된 굵기로
            g.drawLine(firstPointer.x, firstPointer.y, secondPointer.x, secondPointer.y);
        }
        g.dispose(); // 자원 반환
        repaint();
    }
```

### 3. 갤러리

오른쪽 상단의 버튼을 누르면 현재까지 그린 그림을 볼 수 있다.  `MyModalDialog` 에 이 modalDialog 내용이 정의되어 있다. 그림은 총 3개까지 저장되며, 그 이상 그림이 추가되면 FIFO형식으로 그림이 바뀐다.

```java
public static void newPaintSave(Image img){ //저장할 그림을 변수로 받는다.
        switch (num%3){
            case 0:
                if(saveImage_1 != null) background.remove(saveImage_1);
                //이미 저장된 그림이 있다면 제거
                saveImage_1 = new JLabel(new ImageIcon(img)); // 새로운 JLabel 생성하여 그림 저장
                saveImage_1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        returnImage = img;
                        clickSound.clickSound();
                        Window win = SwingUtilities.getWindowAncestor(saveImage_1);
                        if (win != null) { // 그림이 선택되면 창을 닫는다.
                            win.dispose();  
                        }
                    }
                });
                saveImage_1.setBounds(10, 20, 300, 300);
                background.add(saveImage_1);
                background.repaint();
                num++; // 총 저장된 그림의 개수
                break;
...
```

![갤러리](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/e7d7c2d1-7cfd-4f4e-8565-843ecf595b19/Untitled.png)

갤러리

![그림 전시](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/2f2f6ef2-61d6-4c7a-809c-67652c8cd8bd/Untitled.png)

그림 전시

갤러리에 저장된 그림은 담벼락에 전시하는 것이 가능하다.

```java
public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "그림을 전시할까?", "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                            dialog.setVisible(true);
                            requestFocusInWindow();
                            if(returnImage == null){ //dialog로부터 그림을 받지 못한 경우
                                JOptionPane.showMessageDialog(null, "선택한 그림이 없다",
                                        "안내", JOptionPane.INFORMATION_MESSAGE);
                            }else{//dialog로부터 전달받은 그림을 전시한다.
                                album.setIcon(new ImageIcon(returnImage));
                                returnImage = null;
                            }
                }
            }
```

### 4. NPC

NPC는 총 2명이 존재한다. NPC들은 모두 그림을 원하며, 캐릭터가 그린 그림을 전달해줄 수 있다. 이 NPC들 전부에게 그림을 가져다주었는지로 엔딩이 판가름난다.

![고양이](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/ec8b4dca-3a8c-47c0-8155-59abee5628db/Untitled.png)

고양이

![남자](https://prod-files-secure.s3.us-west-2.amazonaws.com/e975e76a-cf5c-4404-816c-b88719d65c34/1a21a7f3-4ea6-49a0-b8b8-441eb95306c0/Untitled.png)

남자

1) 고양이

고양이는 치즈 그림을 원하는 NPC이다. catTalk 변수를 사용하여서 고양이에게 말을 건 횟수와 치즈 그림을 건넸는지에 따라서 다른 반응을 보이도록 구성하였다.

- 처음 말을 건 경우 : *“냐옹”*
- 두번째 말을 건 경우 : *“배고파. 치즈 좀 그려줘. ”*
- 두번째 말을 건 이후에는 치즈 그림을 전달해줄 수 있다. dialog를 사용하여 갤러리에 저장된 그림을 전달할 수 있다.
- 치즈 그림을 전달한 이후에는,
    
    *"이걸로 쥐를 잡을거야", "고마워!", "냐옹", "넌 최고의 화가야."*
    
    라는 대사를 랜덤으로 보여준다.
    

2) 남자

남자는 행복해지는 그림을 원하는 NPC이다. manTalk 변수를 사용하여서 남자에게 말을 건 횟수와 그림을 건넸는지에 따라 다른 반응을 보이도록 구성하였다.

- 처음 말을 건 경우 : *“뭐야”*
- 처음 말을 건 이후에는 행복해지는 그림을 전달해줄 수 있다. dialog를 사용하여 갤러리에 저장된 그림을 전달할 수 있다.
- 그림을 전달한 이후에는,
*"오랜만이네, 이런거", "...고마워", "나도 옛날에는...", "마음에 들어"*
    
    라는 대사를 랜덤으로 보여준다.
    

랜덤으로 대사를 보여주는 것은 `Random random.nextInt(4);`를 사용하여서 구현하였다. 고양이와 남자의 대사는 3초 후 자동으로 사라진다. 이는 Timer를 사용하여서 구현하였다.

```java
// catmessage.setVisible(true)가 되는 순간 catTimer가 실행되게 구성하였다.
catTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catmessage.setVisible(false); //catmessage가 보이지 않도록
                repaint();
                catTimer.stop(); //Timer를 종료한다.
            }
        });
```

### 5. 배터리 및 엔딩

이 게임은 로봇인 주인공의 배터리가 다 닳을때까지 진행된다. 배터리는 총 5칸으로 구성되어 있으며 30초마다 한 칸씩 줄어든다. 

다만, 이 시간은 주인공이 그림을 그리는 동안에는 멈춘다.

```java
timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                D_day--;
                switch (D_day){
                // case 4~1로 배터리 이미지를 바꾸어준다.
                    case 4:
                        battery.setIcon(new ImageIcon("images/component/battery_d4.png"));
                        break;
                    case 3:
                        battery.setIcon(new ImageIcon("images/component/battery_d3.png"));
                        break;
                    case 2:
                        battery.setIcon(new ImageIcon("images/component/battery_d2.png"));
                        break;
                    case 1:
                        battery.setIcon(new ImageIcon("images/component/battery_d1.png"));
                        break;
                    case 0:
                        if(catTalk == 3 && manTalk == 2){
                        // 만약 고양이와 남자에게 모두 그림을 건네준 상황이라면 해피엔딩
                            dispose();
                            new HappyEnding();
                            mainStreetClip.close();
                            break;
                        }
                        // 그렇지 않다면 그냥 게임이 종료된다.
                        EndingMessage.setVisible(true);
                        repaint();
                        break;
                }
            }
        });
        timer.start(); // 게임이 시작됨과 동시에 Timer가 실행된다.
```

## IV. 참고한 사이트 및 정보

### 1. 참고 사이트

그림판 구현

[https://siloam72761.tistory.com/entry/Java-자바로-그림판-만들기펜-도형-파일-읽기-쓰기-색깔-굵기](https://siloam72761.tistory.com/entry/Java-%EC%9E%90%EB%B0%94%EB%A1%9C-%EA%B7%B8%EB%A6%BC%ED%8C%90-%EB%A7%8C%EB%93%A4%EA%B8%B0%ED%8E%9C-%EB%8F%84%ED%98%95-%ED%8C%8C%EC%9D%BC-%EC%9D%BD%EA%B8%B0-%EC%93%B0%EA%B8%B0-%EC%83%89%EA%B9%94-%EA%B5%B5%EA%B8%B0)

JFrame

https://stackoverflow.com/questions/37470884/how-to-dispose-jframe

JLabel

https://blog.naver.com/reeeh/220436765640

https://blog.naver.com/helloworld8/220108747752

Timer

https://sjh836.tistory.com/36

BufferedImage

https://m.blog.naver.com/scyan2011/221784331581

JButton

[https://itbiz-on.tistory.com/entry/자바-swing에서-버튼-클래스-스타일-변경](https://itbiz-on.tistory.com/entry/%EC%9E%90%EB%B0%94-swing%EC%97%90%EC%84%9C-%EB%B2%84%ED%8A%BC-%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%8A%A4%ED%83%80%EC%9D%BC-%EB%B3%80%EA%B2%BD)

https://the-illusionist.me/42

https://stackoverflow.com/questions/16057124/jbutton-with-icon-and-text-but-hide-text

### 2. 정보

cat 그림은 픽사베이 무료 이미지를 사용하였으며, 남자 그림은 친구가 그려준 그림을 사용하였습니다.

그밖에 그림들(주인공, 배경 등)은 전부 제가 그렸습니다.

** 사용한 아이콘 출처 **

<a href="https://www.flaticon.com/kr/free-icons/-" title="드로잉 브러쉬 아이콘">드로잉 브러쉬 아이콘 제작자: Icon home - Flaticon</a>

<a href="https://www.flaticon.com/kr/free-icons/" title="지우개 아이콘">지우개 아이콘 제작자: iconixar - Flaticon</a>

<a href="https://www.flaticon.com/kr/free-icons/" title="페인트 아이콘">페인트 아이콘 제작자: Vectors Market - Flaticon</a>

<a href="https://www.flaticon.com/kr/free-icons/" title="갤러리 아이콘">갤러리 아이콘 제작자: adrianadam - Flaticon</a>
