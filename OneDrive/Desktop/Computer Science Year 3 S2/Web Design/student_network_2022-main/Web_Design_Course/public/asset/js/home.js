const drawRectangular = (ctx, x, y) => {
    ctx.fillRect(x, y, 150, 100);
    ctx.strokeRect(x, y, 150, 100);
  };
  
  const canvas = document.querySelector("#my-canvas");
  if (canvas.getContext) {
    let ctx = canvas.getContext("2d");
  
    ctx.fillStyle = "#F0DB4F";
    ctx.strokeStyle = "red";
  
    let x = 50;
    let y = 50;
    for (let i = 0; i < 3; i++) {
      console.log(i);
      drawRectangular(ctx, x, y);
      x = x + 200;
    }
  }
  console.log(canvas);