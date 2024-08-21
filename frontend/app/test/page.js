'use client';

import { useState } from 'react';
import axios from 'axios';

export default function FileUploader() {
  const [file, setFile] = useState(null);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file) {
      setError('No file selected.');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await axios.post('/api/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });

      setSuccess(response.data.message);
      setError('');
    } catch (error) {
      setError(`Error uploading file: ${error.response?.data?.error || error.message}`);
      setSuccess('');
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input type="file" onChange={handleFileChange} />
        <button type="submit">Upload</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
    </div>
  );
}



// ==========================================================
// 'use client';

// import { useState } from 'react';

// export default function FileUploader() {
//   const [file, setFile] = useState(null);
//   const [error, setError] = useState('');
//   const [success, setSuccess] = useState('');

//   const handleFileChange = (e) => {
//     setFile(e.target.files[0]);
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     if (!file) {
//       setError('No file selected.');
//       return;
//     }

//     const formData = new FormData();
//     formData.append('file', file);

//     try {
//       const response = await fetch('/api/upload', {
//         method: 'POST',
//         body: formData,
//       });

//       const result = await response.json();
//       if (!response.ok) {
//         throw new Error(result.error || 'Upload failed.');
//       }
//       setSuccess('File uploaded successfully!');
//     } catch (error) {
//         console.error(error.message);
//       setError(`Error uploading file: ${error.message}`);
//     }
//   };

//   return (
//     <div>
//       <form onSubmit={handleSubmit}>
//         <input type="file" onChange={handleFileChange} />
//         <button type="submit">Upload</button>
//       </form>
//       {error && <p style={{ color: 'red' }}>{error}</p>}
//       {success && <p style={{ color: 'green' }}>{success}</p>}
//     </div>
//   );
// }
