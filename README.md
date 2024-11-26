Random Chat Application

📚 개요
**Random Chat Application**은 사용자 간 1:1 랜덤 채팅을 제공하는 실시간 메시징 애플리케이션입니다.

사용자는 랜덤으로 매칭되어 실시간으로 채팅을 시작할 수 있습니다.
안정성과 확장성을 고려한 Kafka 기반 메시징 구조로 설계되었습니다.
매칭 대기, 성공 메시지 알림 및 실시간 채팅 기능을 제공합니다.
🛠️ 사용된 기술 스택
기술 영역	기술 명
Backend	Java, Spring Boot, Spring WebSocket
Frontend	HTML5, JavaScript (ES6+), SockJS, STOMP.js
Messaging	Apache Kafka, STOMP Protocol
DevOps	Docker, GitHub
Database	Redis 또는 MySQL (선택)
🗂️ 아키텍처
rust
코드 복사
사용자 A <--> Spring WebSocket <--> Apache Kafka <--> Spring WebSocket <--> 사용자 B
구조 설명
클라이언트:
SockJS 및 STOMP.js를 활용해 서버와 WebSocket 연결을 유지.
메시지 수신 및 채팅 화면 UI 관리.
백엔드:
Spring WebSocket을 활용해 실시간 통신 처리.
Apache Kafka를 통해 메시지를 비동기로 처리하여 확장성 확보.
데이터 흐름:
사용자는 /app/startRandomChat로 요청을 보냄.
서버는 Kafka를 통해 메시지를 비동기 처리 후, /user/{userId}/topic/chat 경로로 응답을 전송.
📋 주요 기능
1:1 랜덤 채팅 매칭:
사용자는 접속 시 대기열에 추가되고, 다른 사용자와 매칭되어 채팅이 시작됩니다.
실시간 메시징:
WebSocket을 활용해 실시간으로 채팅 메시지를 주고받습니다.
매칭 상태 알림:
대기 상태, 매칭 성공 메시지를 실시간으로 표시합니다.
Kafka 기반 확장성:
대규모 트래픽 처리와 메시지 순서를 보장하기 위한 Kafka 통합.
