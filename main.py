from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from werkzeug.security import generate_password_hash, check_password_hash

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///users.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    email = db.Column(db.String(100), unique=True, nullable=False)
    username = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(200), nullable=False)


@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()

    name = data.get('name')
    email = data.get('email')
    username = data.get('username')
    password = data.get('password')

    if not all([username, email, password]):
        return jsonify({'message': 'Invalid data'}), 400

    new_user = User(name=name, email=email, username=username, password=password)
    db.session.add(new_user)
    db.session.commit()


    return jsonify({'message': 'User registered successfully'}), 200


@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()

    username = data.get('username')
    password = data.get('password')



    user = User.query.filter_by(username= data['username'] , password = data['password']).first()



    if user:
        return jsonify({"message":"login successfull"}),200
    else:
        return jsonify({"message": "invalid mail or password"}), 401


if __name__ == '__main__':
    with app.app_context():
     db.create_all()
     app.run(host="0.0.0.0", debug=True)
