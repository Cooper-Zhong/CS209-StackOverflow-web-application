<template>
  <div class="card__container">
    <div class="introduction-card">
      <div class="card__image-container">
      </div>

      <svg class="card__svg" viewBox="0 0 800 500">

        <path d="M 0 100 Q 50 200 100 250 Q 250 400 350 300 C 400 250 550 150 650 300 Q 750 450 800 400 L 800 500 L 0 500" stroke="transparent" fill="#333"></path>
        <path class="card__line" d="M 0 100 Q 50 200 100 250 Q 250 400 350 300 C 400 250 550 150 650 300 Q 750 450 800 400" stroke="pink" stroke-width="3" fill="transparent"></path>
      </svg>

      <div class="card__content">
        <p class="card__title">Stack Overflow Q&A Analysis</p>
        <p>Stack Overflow is a Q&A website for programmers. Stack Overflow serves as a platform for users to ask and answer questions, and, through membership and active
          participation, to vote questions and answers up or down and edit questions and answers in a fashion similar
          to a wiki.
          <br>
          <br>
          In this final project, We use Spring Boot to develop a web application that stores, analyzes, and visualizes
          Stack Overflow Q&A data w.r.t. java programming, with the purpose of understanding the common
          questions, answers, and resolution activities associated with Java programming.</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'introductionPage',
}
</script>

<style>
.card__container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 350px;
  background: #444;
  border-radius: 10px;
}

.introduction-card {
  position: relative;
  background: #333;
  width: 55%;
  height: 350px;
  border-radius: 10px;
  padding: 2rem;
  color: #aaa;
  box-shadow: 0 .25rem .25rem rgba(0,0,0,0.2),
  0 0 1rem rgba(0,0,0,0.2);
  overflow: hidden;
}

.card__image-container {
  margin: -2rem -2rem 1rem -2rem;
}

.card__line {
  opacity: 0;
  animation: LineFadeIn .8s .8s forwards ease-in;
}

.card__image {
  opacity: 0;
  animation: ImageFadeIn .8s 1.4s forwards;
}

.card__title {
  text-align: center; /* 添加这一行，将文字左对齐 */
  color: white;
  margin-top: 35px;
  margin-bottom: 10px;
  font-weight: 800;
  letter-spacing: 0.01em;
  font-size: 24px;
}

.card__content {
  text-align: left; /* 添加这一行，将文字左对齐 */
  margin-top: -1rem;
  opacity: 0;
  animation: ContentFadeIn .8s 1.6s forwards;
}

.card__svg {
  position: absolute;
  left: 0;
  top: 115px;
}

@keyframes LineFadeIn {
  0% {
    opacity: 0;
    d: path("M 0 300 Q 0 300 0 300 Q 0 300 0 300 C 0 300 0 300 0 300 Q 0 300 0 300 ");
    stroke: #fff;
  }

  50% {
    opacity: 1;
    d: path("M 0 300 Q 50 300 100 300 Q 250 300 350 300 C 350 300 500 300 650 300 Q 750 300 800 300");
    stroke: #888BFF;
  }

  100% {
    opacity: 1;
    d: path("M -2 100 Q 50 200 100 250 Q 250 400 350 300 C 400 250 550 150 650 300 Q 750 450 802 400");
    stroke: #545581;
  }
}

@keyframes ContentFadeIn {
  0% {
    transform: translateY(-1rem);
    opacity: 0;
  }

  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes ImageFadeIn {
  0% {
    transform: translate(-.5rem, -.5rem) scale(1.05);
    opacity: 0;
    filter: blur(2px);
  }

  50% {
    opacity: 1;
    filter: blur(2px);
  }

  100% {
    transform: translateY(0) scale(1.0);
    opacity: 1;
    filter: blur(0);
  }
}
</style>