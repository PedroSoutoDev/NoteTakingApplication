import React from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const config = {
    headers: { 'Content-Type': 'text/plain' }
  }

  const [idText, setIdText] = React.useState('');
  const [bodyText, setBodyText] = React.useState('');
  const [noteList, setNoteList] = React.useState('');
  
  const onSaveClick =() => {
    // Fill out the text box, then click save
    // Create new note and refresh the list of notes, the new one should appear
    if (idText === "" && bodyText !== "") {
      axios.post(`/store`, bodyText, config) 
        .then((res) => {
          setBodyText("");
          getAllNotes();
        })
        .catch(console.log);
    }

    // Fill out the text box AND fill out the _id field with an existing _id
    // Updates the existing note and refreshes the list, assume it will always exist
    if (idText !== "" && bodyText !== "") {
      axios.post(`/update?_id=${idText}`, bodyText, config) 
        .then((res) => {
          setIdText("");
          setBodyText("");
          getAllNotes();
        })
        .catch(console.log);
    }
  };

  const onDeleteClick =() => {
    // Fill out the _id field
    // Deletes the note, refreshes the list
    if (idText !== "" && bodyText === "") {
      axios.post(`/delete?_id=${idText}`, "", config) 
        .then((res) => {
          setIdText("");
          setBodyText("");
          getAllNotes();
        })
        .catch(console.log);
    }
  };

  const getAllNotes =() => {
    axios.get(`/list`)
    .then((res) => {
      const temp = res.data.response

      setNoteList(temp.map(note => {
        return (
          <div className="App-items">
            <div className="App-text">{note._id}</div>
            <div className="App-text">{note.data}</div>
          </div>
        )
      }))
      console.log(noteList)
    })
    .catch()
  };

return (
  <div>
    <header>
      <div className="App-idInput">
        <input placeholder="ID" value={idText} onChange={e => setIdText(e.target.value)} style={{width:"500px"}}/>
      </div>

      <div className="App-textInput">
        <textarea placeholder="Note text" value={bodyText} onChange={e => setBodyText(e.target.value)} class="TextboxStyle"/>
      </div>

      <div className="App-buttons">
        <button className="App-greenButton" onClick={onSaveClick}>Save</button>
        <button className="App-redButton" onClick={onDeleteClick}>Delete</button>
      </div>

      <div className="App-notes">
        {noteList}
      </div>
    {getAllNotes()}
    </header>
  </div>
);
}

export default App;
