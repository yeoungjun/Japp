#-*- coding: utf-8 -*-
import os
import sys
import pnp_core

from multiprocessing import Process
from werkzeug import secure_filename
from flask import Flask, request, redirect, url_for, send_file, render_template


UPLOAD_FOLDER = '/flask/upload/'

ALLOWED_EXTENSIONS = set(['apk','zip'])

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


def start(filename):
  result =[]
  input_file = "./upload/"+filename
  start = pnp_core.Smishing_Analysis()
  ret = start.init(input_file)
  if (ret==True):
     result += start.Engine()
  else:
    result += "File open Error"

  # proc = Process(target=start.DynamicEngine)             
  # proc.start()
  return render_template('result.html', result=result)

def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS


@app.route('/', methods=['GET', 'POST'])
def upload_file():
    if request.method == 'POST':
        file = request.files['file']
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            return start(filename)
    return '''
    <!doctype html>
    <title>R&I Security</title>

    <h1>Smishing solution</h1>
    <form action="" method=post enctype=multipart/form-data>
      <p><input type=file name=file>
         <input type=submit value=Upload>
    </form>
    '''


app.run(debug=True, host='0.0.0.0', port=80)  #all ip 80 port

