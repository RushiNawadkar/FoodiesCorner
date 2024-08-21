import fs from 'fs';
import path from 'path';
import { NextResponse } from 'next/server';

export async function POST(request) {
  try {
    const formData = await request.formData();
    const file = formData.get('file');
    console.warn("reached ...")
    if (!file ) {
      console.error("File object is not valid");
      return NextResponse.json({ error: 'unable to upload file' }, { status: 400 });
    }

    const buffer = await file.arrayBuffer();
    const filePath = path.join(process.cwd(), 'public/static/images', file.name);

    fs.writeFileSync(filePath, Buffer.from(buffer));
    return NextResponse.json({ message: 'File uploaded successfully' });
  } catch (error) {
    return NextResponse.json({ error: 'Error uploading file' }, { status: 500 });
  }
}




// ==========================================================
// import fs from 'fs';
// import path from 'path';
// import { NextResponse } from 'next/server';

// export async function POST(request) {
//   try {
//     const formData = await request.formData();
//     const file = formData.get('file');
//     console.log("server : ",file);
//     if (!file || typeof file !== 'object' || !file.file) {
//       return NextResponse.json({ error: 'No file uploaded' }, { status: 400 });
//     }

//     const buffer = await file.arrayBuffer();
//     const filePath = path.join(process.cwd(), 'public', file.name);

//     fs.writeFileSync(filePath, Buffer.from(buffer));
//     return NextResponse.json({ message: 'File uploaded successfully' });
//   } catch (error) {
//     return NextResponse.json({ error: 'Error uploading file' }, { status: 500 });
//   }
// }
