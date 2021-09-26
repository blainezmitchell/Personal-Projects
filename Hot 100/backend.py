from flask import Flask
from flask_restful import Resource, Api, reqparse
import pandas as pd

# initialize api
app = Flask(__name__)
api = Api(app)

# define an endpoint
class Songs(Resource):

    '''
    Reads data from csv file before sending it to requester
    @return the data from the csv file
    '''
    def get(self):
        data = pd.read_csv('output.csv')
        data = data.to_dict()
        return {'data': data}, 200

# link Songs class to /songs endpoint
api.add_resource(Songs, '/songs')

# run the server
if __name__ == '__main__':
    app.run()